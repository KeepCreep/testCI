import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AppTestSharedModule } from 'app/shared';
import {
    EaterComponent,
    EaterDetailComponent,
    EaterUpdateComponent,
    EaterDeletePopupComponent,
    EaterDeleteDialogComponent,
    eaterRoute,
    eaterPopupRoute
} from './';

const ENTITY_STATES = [...eaterRoute, ...eaterPopupRoute];

@NgModule({
    imports: [AppTestSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [EaterComponent, EaterDetailComponent, EaterUpdateComponent, EaterDeleteDialogComponent, EaterDeletePopupComponent],
    entryComponents: [EaterComponent, EaterUpdateComponent, EaterDeleteDialogComponent, EaterDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppTestEaterModule {}
