import { Router, Request, Response, NextFunction } from "express";
import { UserController } from '../controllers/user.controller';
import { authenticateJWT } from '../middlewares/authenticate-jwt';

const userRoutes = Router();
const userController = new UserController();

userRoutes.post('/register', userController.create.bind(userController));
userRoutes.post('/login', userController.login.bind(userController));
userRoutes.post('/forgot_password', userController.forgotPassword.bind(userController));
userRoutes.post('/reset_password', userController.resetPassword.bind(userController));

userRoutes.get('/profile/:id', ((req: Request, res: Response, next: NextFunction) => {
         authenticateJWT(req, res, next)
     }), userController.getProfile.bind(userController));

userRoutes.patch('/user/:id', userController.update.bind(userController));

//userRoutes.delete('/user/:id', userController.remove.bind(userController));

export default userRoutes;