/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { AppTestTestModule } from '../../../test.module';
import { ShotUpdateComponent } from 'app/entities/shot/shot-update.component';
import { ShotService } from 'app/entities/shot/shot.service';
import { Shot } from 'app/shared/model/shot.model';

describe('Component Tests', () => {
    describe('Shot Management Update Component', () => {
        let comp: ShotUpdateComponent;
        let fixture: ComponentFixture<ShotUpdateComponent>;
        let service: ShotService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AppTestTestModule],
                declarations: [ShotUpdateComponent]
            })
                .overrideTemplate(ShotUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ShotUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ShotService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Shot(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.shot = entity;
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
                    const entity = new Shot();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.shot = entity;
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
