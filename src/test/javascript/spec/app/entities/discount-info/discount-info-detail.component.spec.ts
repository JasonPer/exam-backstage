/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ExamBackstageTestModule } from '../../../test.module';
import { DiscountInfoDetailComponent } from 'app/entities/discount-info/discount-info-detail.component';
import { DiscountInfo } from 'app/shared/model/discount-info.model';

describe('Component Tests', () => {
  describe('DiscountInfo Management Detail Component', () => {
    let comp: DiscountInfoDetailComponent;
    let fixture: ComponentFixture<DiscountInfoDetailComponent>;
    const route = ({ data: of({ discountInfo: new DiscountInfo(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ExamBackstageTestModule],
        declarations: [DiscountInfoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(DiscountInfoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DiscountInfoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.discountInfo).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
