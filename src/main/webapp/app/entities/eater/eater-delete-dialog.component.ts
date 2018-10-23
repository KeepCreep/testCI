import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEater } from 'app/shared/model/eater.model';
import { EaterService } from './eater.service';

@Component({
    selector: 'jhi-eater-delete-dialog',
    templateUrl: './eater-delete-dialog.component.html'
})
export class EaterDeleteDialogComponent {
    eater: IEater;

    constructor(private eaterService: EaterService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.eaterService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'eaterListModification',
                content: 'Deleted an eater'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-eater-delete-popup',
    template: ''
})
export class EaterDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ eater }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(EaterDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.eater = eater;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
