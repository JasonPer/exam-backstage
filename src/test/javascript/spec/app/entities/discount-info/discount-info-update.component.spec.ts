/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { ExamBackstageTestModule } from '../../../test.module';
import { DiscountInfoUpdateComponent } from 'app/entities/discount-info/discount-info-update.component';
import { DiscountInfoService } from 'app/entities/discount-info/discount-info.service';
import { DiscountInfo } from 'app/shared/model/discount-info.model';

describe('Component Tests', () => {
  describe('DiscountInfo Management Update Component', () => {
    let comp: DiscountInfoUpdateComponent;
    let fixture: ComponentFixture<DiscountInfoUpdateComponent>;
    let service: DiscountInfoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ExamBackstageTestModule],
        declarations: [DiscountInfoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(DiscountInfoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DiscountInfoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DiscountInfoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new DiscountInfo(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new DiscountInfo();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
