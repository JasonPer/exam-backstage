/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ExamBackstageTestModule } from '../../../test.module';
import { ProductInfoDetailComponent } from 'app/entities/product-info/product-info-detail.component';
import { ProductInfo } from 'app/shared/model/product-info.model';

describe('Component Tests', () => {
  describe('ProductInfo Management Detail Component', () => {
    let comp: ProductInfoDetailComponent;
    let fixture: ComponentFixture<ProductInfoDetailComponent>;
    const route = ({ data: of({ productInfo: new ProductInfo(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ExamBackstageTestModule],
        declarations: [ProductInfoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ProductInfoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProductInfoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.productInfo).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
