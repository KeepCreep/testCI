import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Eater } from 'app/shared/model/eater.model';
import { EaterService } from './eater.service';
import { EaterComponent } from './eater.component';
import { EaterDetailComponent } from './eater-detail.component';
import { EaterUpdateComponent } from './eater-update.component';
import { EaterDeletePopupComponent } from './eater-delete-dialog.component';
import { IEater } from 'app/shared/model/eater.model';

@Injectable({ providedIn: 'root' })
export class EaterResolve implements Resolve<IEater> {
    constructor(private service: EaterService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((eater: HttpResponse<Eater>) => eater.body));
        }
        return of(new Eater());
    }
}

export const eaterRoute: Routes = [
    {
        path: 'eater',
        component: EaterComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'appTestApp.eater.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'eater/:id/view',
        component: EaterDetailComponent,
        resolve: {
            eater: EaterResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'appTestApp.eater.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'eater/new',
        component: EaterUpdateComponent,
        resolve: {
            eater: EaterResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'appTestApp.eater.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'eater/:id/edit',
        component: EaterUpdateComponent,
        resolve: {
            eater: EaterResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'appTestApp.eater.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const eaterPopupRoute: Routes = [
    {
        path: 'eater/:id/delete',
        component: EaterDeletePopupComponent,
        resolve: {
            eater: EaterResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'appTestApp.eater.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
