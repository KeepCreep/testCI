import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Meal } from 'app/shared/model/meal.model';
import { MealService } from './meal.service';
import { MealComponent } from './meal.component';
import { MealDetailComponent } from './meal-detail.component';
import { MealUpdateComponent } from './meal-update.component';
import { MealDeletePopupComponent } from './meal-delete-dialog.component';
import { IMeal } from 'app/shared/model/meal.model';

@Injectable({ providedIn: 'root' })
export class MealResolve implements Resolve<IMeal> {
    constructor(private service: MealService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((meal: HttpResponse<Meal>) => meal.body));
        }
        return of(new Meal());
    }
}

export const mealRoute: Routes = [
    {
        path: 'meal',
        component: MealComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'appTestApp.meal.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'meal/:id/view',
        component: MealDetailComponent,
        resolve: {
            meal: MealResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'appTestApp.meal.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'meal/new',
        component: MealUpdateComponent,
        resolve: {
            meal: MealResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'appTestApp.meal.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'meal/:id/edit',
        component: MealUpdateComponent,
        resolve: {
            meal: MealResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'appTestApp.meal.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const mealPopupRoute: Routes = [
    {
        path: 'meal/:id/delete',
        component: MealDeletePopupComponent,
        resolve: {
            meal: MealResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'appTestApp.meal.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
