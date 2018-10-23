/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { AppTestTestModule } from '../../../test.module';
import { EaterUpdateComponent } from 'app/entities/eater/eater-update.component';
import { EaterService } from 'app/entities/eater/eater.service';
import { Eater } from 'app/shared/model/eater.model';

describe('Component Tests', () => {
    describe('Eater Management Update Component', () => {
        let comp: EaterUpdateComponent;
        let fixture: ComponentFixture<EaterUpdateComponent>;
        let service: EaterService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AppTestTestModule],
                declarations: [EaterUpdateComponent]
            })
                .overrideTemplate(EaterUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(EaterUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EaterService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Eater(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.eater = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Eater();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.eater = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
