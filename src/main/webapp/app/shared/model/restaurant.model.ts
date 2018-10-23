import { ICommand } from 'app/shared/model//command.model';
import { IMeal } from 'app/shared/model//meal.model';

export interface IRestaurant {
    id?: number;
    name?: string;
    commands?: ICommand[];
    meals?: IMeal[];
}

export class Restaurant implements IRestaurant {
    constructor(public id?: number, public name?: string, public commands?: ICommand[], public meals?: IMeal[]) {}
}
