import 'reflect-metadata';
import dotenv from 'dotenv';
import { AppDataSource } from './database/data-source';
import app from './app';
dotenv.config();

const PORT = parseInt(`${process.env.PORT || 3001}`);

AppDataSource.initialize().then(async () => {
    console.log('Database ok');
    app.listen(PORT, () => {
        console.log("Server is running on http://localhost:" + PORT);
    });
})