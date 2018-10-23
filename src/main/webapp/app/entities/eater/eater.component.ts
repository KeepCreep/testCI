import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IEater } from 'app/shared/model/eater.model';
import { Principal } from 'app/core';
import { EaterService } from './eater.service';

@Component({
    selector: 'jhi-eater',
    templateUrl: './eater.component.html'
})
export class EaterComponent implements OnInit, OnDestroy {
    eaters: IEater[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private eaterService: EaterService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.eaterService.query().subscribe(
            (res: HttpResponse<IEater[]>) => {
                this.eaters = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInEaters();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IEater) {
        return item.id;
    }

    registerChangeInEaters() {
        this.eventSubscriber = this.eventManager.subscribe('eaterListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
