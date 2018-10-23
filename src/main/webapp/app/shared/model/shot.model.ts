import { IEater } from 'app/shared/model//eater.model';
import { IMeal } from 'app/shared/model//meal.model';
import { ICommand } from 'app/shared/model//command.model';

export interface IShot {
    id?: number;
    numberOfMeal?: number;
    eater?: IEater;
    meal?: IMeal;
    command?: ICommand;
}

export class Shot implements IShot {
    constructor(public id?: number, public numberOfMeal?: number, public eater?: IEater, public meal?: IMeal, public command?: ICommand) {}
}
