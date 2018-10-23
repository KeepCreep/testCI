import { IRestaurant } from 'app/shared/model//restaurant.model';

export interface IMeal {
    id?: number;
    name?: string;
    description?: string;
    kcal?: number;
    picture?: string;
    restaurants?: IRestaurant[];
}

export class Meal implements IMeal {
    constructor(
        public id?: number,
        public name?: string,
        public description?: string,
        public kcal?: number,
        public picture?: string,
        public restaurants?: IRestaurant[]
    ) {}
}
