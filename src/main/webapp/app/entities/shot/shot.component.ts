import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IShot } from 'app/shared/model/shot.model';
import { Principal } from 'app/core';
import { ShotService } from './shot.service';

@Component({
    selector: 'jhi-shot',
    templateUrl: './shot.component.html'
})
export class ShotComponent implements OnInit, OnDestroy {
    shots: IShot[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private shotService: ShotService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.shotService.query().subscribe(
            (res: HttpResponse<IShot[]>) => {
                this.shots = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInShots();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IShot) {
        return item.id;
    }

    registerChangeInShots() {
        this.eventSubscriber = this.eventManager.subscribe('shotListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
