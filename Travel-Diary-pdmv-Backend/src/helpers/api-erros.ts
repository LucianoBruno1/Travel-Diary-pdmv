import { ZodError, ZodIssue } from "zod";

export class ApiError extends Error {
    public readonly statusCode: number;

    constructor(message: string, statusCode: number) {
        super(message);
        this.statusCode = statusCode;
    }
}

export class BadRequestError extends ApiError {
    constructor(message: string) {
        super(message, 400);
    }
}

export class NotFoundError extends ApiError {
    constructor(message: string) {
        super(message, 404);
    }
}

export class UnauthorizedError extends ApiError {
    constructor(message: string) {
        super(message, 401);
    }
}

export class AppError {
    constructor(public readonly message: string, public readonly statusCode = 400) { }
}

export class ValidationError {
    public readonly message: string;
    public readonly statusCode: number;
    public readonly issues: ZodIssue[];

    constructor(error: ZodError, statusCode = 400){
        const [firstError] = error.issues;
        const {message} = firstError;

        this.issues = error.issues;
        this.message = message;
        this.statusCode = statusCode;
    }
}