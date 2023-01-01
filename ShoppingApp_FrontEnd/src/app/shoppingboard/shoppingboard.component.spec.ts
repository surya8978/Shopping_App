import { ComponentFixture, TestBed } from '@angular/core/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { ChangeDetectorRef } from '@angular/core';
import { ProductService } from '../services/productservice';
import { FormsModule } from '@angular/forms';
import { ShoppingboardComponent } from './shoppingboard.component';

describe('ShoppingboardComponent', () => {
  let component: ShoppingboardComponent;
  let fixture: ComponentFixture<ShoppingboardComponent>;

  beforeEach(() => {
    const changeDetectorRefStub = () => ({ detectChanges: () => ({}) });
    const productServiceStub = () => ({
      allProducts: () => ({ subscribe: f => f({}) }),
      productbyname: productsname => ({ subscribe: f => f({}) })
    });
    TestBed.configureTestingModule({
      imports: [FormsModule],
      schemas: [NO_ERRORS_SCHEMA],
      declarations: [ShoppingboardComponent],
      providers: [
        { provide: ChangeDetectorRef, useFactory: changeDetectorRefStub },
        { provide: ProductService, useFactory: productServiceStub }
      ]
    });
    fixture = TestBed.createComponent(ShoppingboardComponent);
    component = fixture.componentInstance;
  });

  it('can load instance', () => {
    expect(component).toBeTruthy();
  });

  it(`products has default value`, () => {
    expect(component.products).toEqual([]);
  });

  it(`responses has default value`, () => {
    expect(component.responses).toEqual([]);
  });

  describe('ngOnInit', () => {
    it('makes expected calls', () => {
      spyOn(component, 'onSearchAll').and.callThrough();
      component.ngOnInit();
      expect(component.onSearchAll).toHaveBeenCalled();
    });
  });

  describe('onSearchAll', () => {
    it('makes expected calls', () => {
      const productServiceStub: ProductService = fixture.debugElement.injector.get(
        ProductService
      );
      spyOn(productServiceStub, 'allProducts').and.callThrough();
      component.onSearchAll();
      expect(productServiceStub.allProducts).toHaveBeenCalled();
    });
  });

  describe('OnSearch', () => {
    it('makes expected calls', () => {
      const productServiceStub: ProductService = fixture.debugElement.injector.get(
        ProductService
      );
      spyOn(component, 'onSearchAll').and.callThrough();
      spyOn(productServiceStub, 'productbyname').and.callThrough();
      component.OnSearch();
      expect(productServiceStub.productbyname).toHaveBeenCalled();
    });
  });


});
