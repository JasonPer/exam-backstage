/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { ExamBackstageTestModule } from '../../../test.module';
import { ProductInfoUpdateComponent } from 'app/entities/product-info/product-info-update.component';
import { ProductInfoService } from 'app/entities/product-info/product-info.service';
import { ProductInfo } from 'app/shared/model/product-info.model';

describe('Component Tests', () => {
  describe('ProductInfo Management Update Component', () => {
    let comp: ProductInfoUpdateComponent;
    let fixture: ComponentFixture<ProductInfoUpdateComponent>;
    let service: ProductInfoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ExamBackstageTestModule],
        declarations: [ProductInfoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ProductInfoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProductInfoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProductInfoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ProductInfo(123);
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
        const entity = new ProductInfo();
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
