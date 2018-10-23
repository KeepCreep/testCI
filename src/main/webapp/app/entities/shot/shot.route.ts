import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Shot } from 'app/shared/model/shot.model';
import { ShotService } from './shot.service';
import { ShotComponent } from './shot.component';
import { ShotDetailComponent } from './shot-detail.component';
import { ShotUpdateComponent } from './shot-update.component';
import { ShotDeletePopupComponent } from './shot-delete-dialog.component';
import { IShot } from 'app/shared/model/shot.model';

@Injectable({ providedIn: 'root' })
export class ShotResolve implements Resolve<IShot> {
    constructor(private service: ShotService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((shot: HttpResponse<Shot>) => shot.body));
        }
        return of(new Shot());
    }
}

export const shotRoute: Routes = [
    {
        path: 'shot',
        component: ShotComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'appTestApp.shot.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'shot/:id/view',
        component: ShotDetailComponent,
        resolve: {
            shot: ShotResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'appTestApp.shot.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'shot/new',
        component: ShotUpdateComponent,
        resolve: {
            shot: ShotResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'appTestApp.shot.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'shot/:id/edit',
        component: ShotUpdateComponent,
        resolve: {
            shot: ShotResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'appTestApp.shot.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const shotPopupRoute: Routes = [
    {
        path: 'shot/:id/delete',
        component: ShotDeletePopupComponent,
        resolve: {
            shot: ShotResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'appTestApp.shot.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
