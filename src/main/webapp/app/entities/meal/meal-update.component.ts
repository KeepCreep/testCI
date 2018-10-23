import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IMeal } from 'app/shared/model/meal.model';
import { MealService } from './meal.service';
import { IRestaurant } from 'app/shared/model/restaurant.model';
import { RestaurantService } from 'app/entities/restaurant';

@Component({
    selector: 'jhi-meal-update',
    templateUrl: './meal-update.component.html'
})
export class MealUpdateComponent implements OnInit {
    private _meal: IMeal;
    isSaving: boolean;

    restaurants: IRestaurant[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private mealService: MealService,
        private restaurantService: RestaurantService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ meal }) => {
            this.meal = meal;
        });
        this.restaurantService.query().subscribe(
            (res: HttpResponse<IRestaurant[]>) => {
                this.restaurants = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.meal.id !== undefined) {
            this.subscribeToSaveResponse(this.mealService.update(this.meal));
        } else {
            this.subscribeToSaveResponse(this.mealService.create(this.meal));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IMeal>>) {
        result.subscribe((res: HttpResponse<IMeal>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackRestaurantById(index: number, item: IRestaurant) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
    get meal() {
        return this._meal;
    }

    set meal(meal: IMeal) {
        this._meal = meal;
    }
}
