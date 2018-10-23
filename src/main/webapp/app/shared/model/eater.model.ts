import { ICommand } from 'app/shared/model//command.model';

export interface IEater {
    id?: number;
    pseudo?: string;
    firstName?: string;
    lastName?: string;
    commands?: ICommand[];
}

export class Eater implements IEater {
    constructor(
        public id?: number,
        public pseudo?: string,
        public firstName?: string,
        public lastName?: string,
        public commands?: ICommand[]
    ) {}
}
