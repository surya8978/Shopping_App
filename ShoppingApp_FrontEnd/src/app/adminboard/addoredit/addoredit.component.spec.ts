import { ComponentFixture, TestBed } from '@angular/core/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ProductService } from 'src/app/services/productservice';
import { FormsModule } from '@angular/forms';
import { AddoreditComponent } from './addoredit.component';

describe('AddoreditComponent', () => {
  let component: AddoreditComponent;
  let fixture: ComponentFixture<AddoreditComponent>;

  beforeEach(() => {
    const activatedRouteStub = () => ({
      snapshot: { paramMap: { get: () => ({}) } }
    });
    const productServiceStub = () => ({
      productbyid: productId => ({ subscribe: f => f({}) }),
      UpdateProduct: product => ({ subscribe: f => f({}) }),
      addProducts: product => ({ subscribe: f => f({}) })
    });
    TestBed.configureTestingModule({
      imports: [FormsModule],
      schemas: [NO_ERRORS_SCHEMA],
      declarations: [AddoreditComponent],
      providers: [
        { provide: ActivatedRoute, useFactory: activatedRouteStub },
        { provide: ProductService, useFactory: productServiceStub }
      ]
    });
    fixture = TestBed.createComponent(AddoreditComponent);
    component = fixture.componentInstance;
  });

  it('can load instance', () => {
    expect(component).toBeTruthy();
  });

  it(`isAdded has default value`, () => {
    expect(component.isAdded).toEqual(false);
  });

  it(`isEdit has default value`, () => {
    expect(component.isEdit).toEqual(false);
  });

  it(`Edit has default value`, () => {
    expect(component.Edit).toEqual(false);
  });

  describe('ngOnInit', () => {
    it('makes expected calls', () => {
      const productServiceStub: ProductService = fixture.debugElement.injector.get(
        ProductService
      );
      spyOn(productServiceStub, 'productbyid').and.callThrough();
      component.ngOnInit();
      expect(productServiceStub.productbyid).toHaveBeenCalled();
    });
  });

  describe('onSubmit', () => {
    it('makes expected calls', () => {
      const productServiceStub: ProductService = fixture.debugElement.injector.get(
        ProductService
      );
      spyOn(productServiceStub, 'UpdateProduct').and.callThrough();
      spyOn(productServiceStub, 'addProducts').and.callThrough();
      component.onSubmit();
      expect(productServiceStub.addProducts).toHaveBeenCalled();
    });
  });
});
