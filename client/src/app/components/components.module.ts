
import { HomeHeaderComponent } from './home-header/home-header.component';
import { CompanyTableComponent } from './company-table/company-table.component';
import { CompanyEditComponent } from './company-edit/company-edit.component';

import { IPOTableComponent } from './ipo-table/ipo-table.component';
import { IPOEditComponent } from './ipo-edit/ipo-edit.component';
import { PriceFileImportComponent } from './price-file-import/price-file-import.component';
import { CommonModule } from '@angular/common';  
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import {MatSelectModule} from '@angular/material/select';
import { ReactiveFormsModule } from '@angular/forms';
import { FormsModule } from '@angular/forms';
import {MatButtonModule} from '@angular/material/button'
import {MatSortModule} from '@angular/material/sort';
import {MatTableModule} from '@angular/material/table';
import {MatPaginatorModule} from '@angular/material/paginator';
import { NgModule } from '@angular/core';
import {MatSidenavModule} from '@angular/material/sidenav';
import {MatDatepickerModule} from '@angular/material/datepicker';
import {MatNativeDateModule, MatDialogModule, MatAutocomplete, MatAutocompleteModule} from '@angular/material';
import { CompanyComparisonComponent } from './company-comparison/company-comparison.component';
import { NgxEchartsModule } from 'ngx-echarts';
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';

@NgModule({
  imports: [ 
    MatSidenavModule ,
    MatSortModule,
    MatTableModule,
    MatPaginatorModule,
    CommonModule,
    MatFormFieldModule,
    MatInputModule,
    ReactiveFormsModule,
    FormsModule,
    MatButtonModule,
    MatSelectModule,
    MatDatepickerModule,
    MatDialogModule,
    MatAutocompleteModule,
    NgxEchartsModule,
    MatNativeDateModule,
    MatProgressSpinnerModule
    
  ],
  declarations: [
    HomeHeaderComponent,
    CompanyTableComponent,
    PriceFileImportComponent,
    CompanyEditComponent,
    IPOTableComponent,
    IPOEditComponent,
    CompanyComparisonComponent  
  ],
  exports: [
    HomeHeaderComponent,
    CompanyTableComponent,
    PriceFileImportComponent,
    CompanyEditComponent,
    IPOTableComponent,
    IPOEditComponent,
    CompanyComparisonComponent       
  ],
  entryComponents: [
  
  ]
})
export class ComponentsModule { }
