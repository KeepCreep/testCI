import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AppTestSharedModule } from 'app/shared';
import {
    ShotComponent,
    ShotDetailComponent,
    ShotUpdateComponent,
    ShotDeletePopupComponent,
    ShotDeleteDialogComponent,
    shotRoute,
    shotPopupRoute
} from './';

const ENTITY_STATES = [...shotRoute, ...shotPopupRoute];

@NgModule({
    imports: [AppTestSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [ShotComponent, ShotDetailComponent, ShotUpdateComponent, ShotDeleteDialogComponent, ShotDeletePopupComponent],
    entryComponents: [ShotComponent, ShotUpdateComponent, ShotDeleteDialogComponent, ShotDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppTestShotModule {}
