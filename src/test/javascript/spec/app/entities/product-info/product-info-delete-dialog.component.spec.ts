/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ExamBackstageTestModule } from '../../../test.module';
import { ProductInfoDeleteDialogComponent } from 'app/entities/product-info/product-info-delete-dialog.component';
import { ProductInfoService } from 'app/entities/product-info/product-info.service';

describe('Component Tests', () => {
  describe('ProductInfo Management Delete Component', () => {
    let comp: ProductInfoDeleteDialogComponent;
    let fixture: ComponentFixture<ProductInfoDeleteDialogComponent>;
    let service: ProductInfoService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ExamBackstageTestModule],
        declarations: [ProductInfoDeleteDialogComponent]
      })
        .overrideTemplate(ProductInfoDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProductInfoDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProductInfoService);
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
