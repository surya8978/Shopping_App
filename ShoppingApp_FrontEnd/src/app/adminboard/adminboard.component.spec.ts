import { ComponentFixture, TestBed } from '@angular/core/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { ChangeDetectorRef } from '@angular/core';
import { ProductService } from '../services/productservice';
import { RouterTestingModule } from '@angular/router/testing';
import { AdminboardComponent } from './adminboard.component';

describe('AdminboardComponent', () => {
  let component: AdminboardComponent;
  let fixture: ComponentFixture<AdminboardComponent>;

  beforeEach(() => {
    const changeDetectorRefStub = () => ({ detectChanges: () => ({}) });
    const productServiceStub = () => ({
      allProducts: () => ({ subscribe: f => f({}) }),
      deleteProduct: productId => ({ subscribe: f => f({}) })
    });
    TestBed.configureTestingModule({
      imports: [RouterTestingModule],
      schemas: [NO_ERRORS_SCHEMA],
      declarations: [AdminboardComponent],
      providers: [
        { provide: ChangeDetectorRef, useFactory: changeDetectorRefStub },
        { provide: ProductService, useFactory: productServiceStub }
      ]
    });
    fixture = TestBed.createComponent(AdminboardComponent);
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
      const productServiceStub: ProductService = fixture.debugElement.injector.get(
        ProductService
      );
      spyOn(productServiceStub, 'allProducts').and.callThrough();
      component.ngOnInit();
      expect(productServiceStub.allProducts).toHaveBeenCalled();
    });
  });

});
