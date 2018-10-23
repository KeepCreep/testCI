import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { ICommand } from 'app/shared/model/command.model';
import { CommandService } from './command.service';
import { IRestaurant } from 'app/shared/model/restaurant.model';
import { RestaurantService } from 'app/entities/restaurant';
import { IEater } from 'app/shared/model/eater.model';
import { EaterService } from 'app/entities/eater';

@Component({
    selector: 'jhi-command-update',
    templateUrl: './command-update.component.html'
})
export class CommandUpdateComponent implements OnInit {
    private _command: ICommand;
    isSaving: boolean;

    restaurants: IRestaurant[];

    eaters: IEater[];
    creationDate: string;

    constructor(
        private jhiAlertService: JhiAlertService,
        private commandService: CommandService,
        private restaurantService: RestaurantService,
        private eaterService: EaterService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ command }) => {
            this.command = command;
        });
        this.restaurantService.query().subscribe(
            (res: HttpResponse<IRestaurant[]>) => {
                this.restaurants = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.eaterService.query().subscribe(
            (res: HttpResponse<IEater[]>) => {
                this.eaters = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.command.creationDate = moment(this.creationDate, DATE_TIME_FORMAT);
        if (this.command.id !== undefined) {
            this.subscribeToSaveResponse(this.commandService.update(this.command));
        } else {
            this.subscribeToSaveResponse(this.commandService.create(this.command));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ICommand>>) {
        result.subscribe((res: HttpResponse<ICommand>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackEaterById(index: number, item: IEater) {
        return item.id;
    }
    get command() {
        return this._command;
    }

    set command(command: ICommand) {
        this._command = command;
        this.creationDate = moment(command.creationDate).format(DATE_TIME_FORMAT);
    }
}
