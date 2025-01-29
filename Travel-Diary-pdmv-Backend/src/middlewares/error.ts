import { Request, Response, NextFunction } from 'express';
import { ApiError, NotFoundError, UnauthorizedError } from '../helpers/api-erros';
import { ValidationError } from '../helpers/api-erros'; // Importa sua classe de erro personalizada
import { QueryFailedError } from 'typeorm';
import { MulterError } from 'multer';

export const errorMiddleware = (
  error: Error & Partial<ApiError>,
  _req: Request,
  res: Response,
  _next: NextFunction
) => {
  console.error('Error:', error);

   // Verifica se é um erro do Multer
   if (error instanceof MulterError) {
    let message = 'Erro de upload.';
    if (error.code === 'LIMIT_UNEXPECTED_FILE') {
      message = 'O número máximo de arquivos foi excedido ou campo inesperado foi enviado.';
    } else if (error.code === 'LIMIT_FILE_SIZE') {
      message = 'O tamanho do arquivo enviado excede o limite permitido.';
    } else if (error.code === 'LIMIT_FILE_COUNT') {
      message = 'O número máximo de arquivos permitidos foi excedido.';
    } else if (error.code === 'LIMIT_FIELD_KEY') {
      message = 'O nome do campo enviado é muito longo.';
    }

    return res.status(400).json({
      status: 'error',
      message,
    });
  }

  // Verifica se é erro de entrada duplicada no banco de dados
  if (error instanceof QueryFailedError && (error as any).code === 'ER_DUP_ENTRY') {
    return res.status(409).json({
      status: 'error',
      message: 'Esse dado já existe no banco de dados.',
    });
  }

  // Lida com erros de validação do Zod
  if (error instanceof ValidationError) {
    return res.status(error.statusCode).json({
      status: 'error',
      message: error.message,
      issues: error.issues, // Inclui detalhes do erro
    });
  }

  if (error instanceof UnauthorizedError) {
    return res.status(error.statusCode).json({
      status: 'error',
      message: error.message,
    });
  }

  if (error instanceof NotFoundError) {
    return res.status(error.statusCode).json({
      status: 'error',
      message: error.message,
    });
  }

  // Lida com erros personalizados com statusCode
  if (error instanceof ApiError) {
    return res.status(error.statusCode).json({
      status: 'error',
      message: error.message,
    });
  }

  // Caso contrário, trata como erro interno
  return res.status(500).json({
    status: 'error',
    message: 'Internal Server Error',
  });
};
