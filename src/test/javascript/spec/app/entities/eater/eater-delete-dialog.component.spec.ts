/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AppTestTestModule } from '../../../test.module';
import { EaterDeleteDialogComponent } from 'app/entities/eater/eater-delete-dialog.component';
import { EaterService } from 'app/entities/eater/eater.service';

describe('Component Tests', () => {
    describe('Eater Management Delete Component', () => {
        let comp: EaterDeleteDialogComponent;
        let fixture: ComponentFixture<EaterDeleteDialogComponent>;
        let service: EaterService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AppTestTestModule],
                declarations: [EaterDeleteDialogComponent]
            })
                .overrideTemplate(EaterDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(EaterDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EaterService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
