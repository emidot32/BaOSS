import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {BsDropdownModule} from 'ngx-bootstrap/dropdown';
import {TooltipModule} from 'ngx-bootstrap/tooltip';
import {ModalModule} from 'ngx-bootstrap/modal';
import {FullCalendarModule} from '@fullcalendar/angular'; // for FullCalendar!
import {AppComponent} from './app.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {RegistrationComponent} from './registration/registration.component';
import {AuthorizationComponent} from './authorization/authorization.component';
import {HomeComponent} from './home/home.component';
import {RouterModule, Routes} from '@angular/router';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {HeaderComponent} from './header/header.component';
import {MenuComponent} from './menu/menu.component';
import {
    MatAutocompleteModule,
    MatDatepickerModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule
} from '@angular/material';
import {MatNativeDateModule} from '@angular/material/core';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {ToastrModule} from 'ngx-toastr';

import {InfiniteScrollModule} from 'ngx-infinite-scroll';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {HeaderAuthComponent} from './headerauth/header-auth.component';
import {OffersComponent} from './offers/offers.component';
import {FooterComponent} from './footer/footer.component';
import {ProfileComponent} from './profile/profile.component';
import {HomeathComponent} from './homeath/homeath.component';
import {JwtInterceptor} from './_helpers/jwt.interceptor';
import { EditProfileComponent } from './edit-profile/edit-profile.component';

const homeComponents: Routes = [
    {path: 'offers', component: OffersComponent},
];
const homeAthComponents: Routes = [
    {path: 'offers', component: OffersComponent},
    {path: 'profile/:login', component: ProfileComponent},
    {path: 'profile/:login/edit', component: EditProfileComponent},
];


const appRoutes: Routes = [

    {path: 'home', component: HomeComponent, children: homeComponents},
    {path: 'homeath', component: HomeathComponent, children: homeAthComponents},
    {path: 'login', component: AuthorizationComponent},
    {path: 'register', component: RegistrationComponent},
    // otherwise redirect to home
    {path: '**', redirectTo: 'home/offers'}
];


@NgModule({
    declarations: [
        AppComponent,
        RegistrationComponent,
        AuthorizationComponent,
        HomeComponent,
        HeaderComponent,
        HeaderAuthComponent,
        MenuComponent,
        OffersComponent,
        FooterComponent,
        ProfileComponent,
        HomeathComponent,
        EditProfileComponent,
    ],
    imports: [
        InfiniteScrollModule,
        BrowserModule,
        RouterModule.forRoot(appRoutes),
        FormsModule,
        ReactiveFormsModule,
        NgbModule,
        MatInputModule,
        MatFormFieldModule,
        MatAutocompleteModule,
        FullCalendarModule, // for FullCalendar!
        BsDropdownModule.forRoot(),
        TooltipModule.forRoot(),
        ModalModule.forRoot(),
        HttpClientModule,
        ToastrModule.forRoot(),
        MatAutocompleteModule,
        MatFormFieldModule,
        MatDatepickerModule,
        MatInputModule,
        MatNativeDateModule,
        BrowserAnimationsModule,
        MatSelectModule
    ],
    providers: [{provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true}],
    bootstrap: [AppComponent]
})
export class AppModule {
}
