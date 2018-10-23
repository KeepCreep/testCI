import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IShot } from 'app/shared/model/shot.model';
import { ShotService } from './shot.service';
import { IEater } from 'app/shared/model/eater.model';
import { EaterService } from 'app/entities/eater';
import { IMeal } from 'app/shared/model/meal.model';
import { MealService } from 'app/entities/meal';
import { ICommand } from 'app/shared/model/command.model';
import { CommandService } from 'app/entities/command';

@Component({
    selector: 'jhi-shot-update',
    templateUrl: './shot-update.component.html'
})
export class ShotUpdateComponent implements OnInit {
    private _shot: IShot;
    isSaving: boolean;

    eaters: IEater[];

    meals: IMeal[];

    commands: ICommand[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private shotService: ShotService,
        private eaterService: EaterService,
        private mealService: MealService,
        private commandService: CommandService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ shot }) => {
            this.shot = shot;
        });
        this.eaterService.query().subscribe(
            (res: HttpResponse<IEater[]>) => {
                this.eaters = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.mealService.query().subscribe(
            (res: HttpResponse<IMeal[]>) => {
                this.meals = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.commandService.query().subscribe(
            (res: HttpResponse<ICommand[]>) => {
                this.commands = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.shot.id !== undefined) {
            this.subscribeToSaveResponse(this.shotService.update(this.shot));
        } else {
            this.subscribeToSaveResponse(this.shotService.create(this.shot));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IShot>>) {
        result.subscribe((res: HttpResponse<IShot>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackEaterById(index: number, item: IEater) {
        return item.id;
    }

    trackMealById(index: number, item: IMeal) {
        return item.id;
    }

    trackCommandById(index: number, item: ICommand) {
        return item.id;
    }
    get shot() {
        return this._shot;
    }

    set shot(shot: IShot) {
        this._shot = shot;
    }
}
