import 'express-async-errors';
import express, { Request, Response, NextFunction } from 'express';
import cors from 'cors';
import { errorMiddleware } from './middlewares/error';
import userRoutes from './routes/user.routes';
import photoRoutes from './routes/photo.routes';
import diaryRoutes from './routes/diary.routes';
import mapPointRoutes from './routes/map_point.routes';

const app = express();

app.use(cors());
app.use(express.json());

app.use('/v1/api', userRoutes);
app.use('/v1/api', photoRoutes)
app.use('/v1/api', diaryRoutes)
app.use('/v1/api', mapPointRoutes)

app.use((error: Error, req: Request, res: Response, next: NextFunction) => {
    errorMiddleware(error, req, res, next)
});
 
export default app;