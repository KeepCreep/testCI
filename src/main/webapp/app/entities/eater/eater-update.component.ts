import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IEater } from 'app/shared/model/eater.model';
import { EaterService } from './eater.service';

@Component({
    selector: 'jhi-eater-update',
    templateUrl: './eater-update.component.html'
})
export class EaterUpdateComponent implements OnInit {
    private _eater: IEater;
    isSaving: boolean;

    constructor(private eaterService: EaterService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ eater }) => {
            this.eater = eater;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.eater.id !== undefined) {
            this.subscribeToSaveResponse(this.eaterService.update(this.eater));
        } else {
            this.subscribeToSaveResponse(this.eaterService.create(this.eater));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IEater>>) {
        result.subscribe((res: HttpResponse<IEater>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get eater() {
        return this._eater;
    }

    set eater(eater: IEater) {
        this._eater = eater;
    }
}
