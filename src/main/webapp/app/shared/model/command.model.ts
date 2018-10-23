import { Moment } from 'moment';
import { IRestaurant } from 'app/shared/model//restaurant.model';
import { IEater } from 'app/shared/model//eater.model';

export interface ICommand {
    id?: number;
    creationDate?: Moment;
    name?: string;
    restaurant?: IRestaurant;
    author?: IEater;
}

export class Command implements ICommand {
    constructor(
        public id?: number,
        public creationDate?: Moment,
        public name?: string,
        public restaurant?: IRestaurant,
        public author?: IEater
    ) {}
}
