import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { AppTestEaterModule } from './eater/eater.module';
import { AppTestMealModule } from './meal/meal.module';
import { AppTestCommandModule } from './command/command.module';
import { AppTestShotModule } from './shot/shot.module';
import { AppTestRestaurantModule } from './restaurant/restaurant.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        AppTestEaterModule,
        AppTestMealModule,
        AppTestCommandModule,
        AppTestShotModule,
        AppTestRestaurantModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppTestEntityModule {}
