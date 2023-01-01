import { ComponentFixture, TestBed } from '@angular/core/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { Router } from '@angular/router';
import { AppComponent } from '../app.component';
import { AuthService } from '../services/AuthService';
import { TokenStorageService } from '../services/token-storage.service';
import { FormsModule } from '@angular/forms';
import { LoginComponent } from './login.component';

describe('LoginComponent', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;

  beforeEach(() => {
    const routerStub = () => ({ navigateByUrl: returnUrl => ({}) });
    const appComponentStub = () => ({ auto_logout: () => ({}) });
    const authServiceStub = () => ({
      login: (userid, password) => ({ subscribe: f => f({}) })
    });
    const tokenStorageServiceStub = () => ({
      saveToken: authToken => ({}),
      saveUser: user => ({})
    });
    TestBed.configureTestingModule({
      imports: [FormsModule],
      schemas: [NO_ERRORS_SCHEMA],
      declarations: [LoginComponent],
      providers: [
        { provide: Router, useFactory: routerStub },
        { provide: AppComponent, useFactory: appComponentStub },
        { provide: AuthService, useFactory: authServiceStub },
        { provide: TokenStorageService, useFactory: tokenStorageServiceStub }
      ]
    });
    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
  });

  it('can load instance', () => {
    expect(component).toBeTruthy();
  });

  it(`isLogout has default value`, () => {
    expect(component.isLogout).toEqual(false);
  });

  it(`isInvalid has default value`, () => {
    expect(component.isInvalid).toEqual(false);
  });

  it(`returnUrl has default value`, () => {
    expect(component.returnUrl).toEqual(`/`);
  });

  describe('onSubmit', () => {
    it('makes expected calls', () => {
      const routerStub: Router = fixture.debugElement.injector.get(Router);
      const appComponentStub: AppComponent = fixture.debugElement.injector.get(
        AppComponent
      );
      const authServiceStub: AuthService = fixture.debugElement.injector.get(
        AuthService
      );
      const tokenStorageServiceStub: TokenStorageService = fixture.debugElement.injector.get(
        TokenStorageService
      );
      spyOn(routerStub, 'navigateByUrl').and.callThrough();
      spyOn(appComponentStub, 'auto_logout').and.callThrough();
      spyOn(authServiceStub, 'login').and.callThrough();
      spyOn(tokenStorageServiceStub, 'saveToken').and.callThrough();
      spyOn(tokenStorageServiceStub, 'saveUser').and.callThrough();
      component.onSubmit();
      expect(routerStub.navigateByUrl).toHaveBeenCalled();
      expect(appComponentStub.auto_logout).toHaveBeenCalled();
      expect(authServiceStub.login).toHaveBeenCalled();
      expect(tokenStorageServiceStub.saveToken).toHaveBeenCalled();
      expect(tokenStorageServiceStub.saveUser).toHaveBeenCalled();
    });
  });
});
