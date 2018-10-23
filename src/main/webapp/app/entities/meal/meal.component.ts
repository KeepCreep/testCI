import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IMeal } from 'app/shared/model/meal.model';
import { Principal } from 'app/core';
import { MealService } from './meal.service';

@Component({
    selector: 'jhi-meal',
    templateUrl: './meal.component.html'
})
export class MealComponent implements OnInit, OnDestroy {
    meals: IMeal[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private mealService: MealService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.mealService.query().subscribe(
            (res: HttpResponse<IMeal[]>) => {
                this.meals = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInMeals();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IMeal) {
        return item.id;
    }

    registerChangeInMeals() {
        this.eventSubscriber = this.eventManager.subscribe('mealListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
