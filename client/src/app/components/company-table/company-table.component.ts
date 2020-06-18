import { Component, OnInit, OnDestroy, ViewChild, ElementRef, AfterViewInit, AfterContentInit, ViewChildren, QueryList } from '@angular/core';
// import { UserService } from 'src/app/service/user.service';
import {MatSort, MatTableDataSource, MatTable} from '@angular/material';
import {MatPaginator} from '@angular/material';
import { CompanyService } from 'src/app/service/company.service';
import * as _ from "lodash";
import { LoginService } from 'src/app/service/login-service';

export interface TableElement {
  id: number;
  companyname: string;
  ceoname: string;
  turnover: number;
  briefWriteUp: string;
  boardDirectors: string;
  sector: string;
  stockchangename: string;
  code: string;
  action: string;
}

@Component({
  selector: 'company-table',
  templateUrl: './company-table.component.html',
  styleUrls: ['./company-table.component.scss'],
})
export class CompanyTableComponent implements OnInit,OnDestroy,AfterViewInit,AfterContentInit {

    mockTableData: TableElement[] =
    [
      // {id:1, companyname: "BMW", ceoname : 'John', turnover: 10000, description: '320Li, 520, 730L, X3, X5', ipodata:"",sector:"automotive",stockchangename:"BSE",  code:"BMW", action:""},
      // {id:2, companyname: "Mercedez Benz", ceoname : 'Bill', turnover: 9009, description: 'C200, E300, S300, GLA, GLC GLS',ipodata:"",sector:"automotive",stockchangename:"NSE",  code:"BEN", action:""}
    ];
    tableData: TableElement[] = [];

    @ViewChild(MatTable) companyTable: MatTable<any>;
    @ViewChild(MatSort) sort: MatSort;
    @ViewChild(MatPaginator) paginator: MatPaginator;
    displayedColumns: string[] = [
      'companyname', 'ceoname', 'turnover', 
      'briefWriteUp','boardDirectors','sector',
      'stockchangename',
      'code'
    ];
    dataSource: any;

    pushRightClass: string = 'push-right';
    userRole:string;
    showAdminMenu: boolean;
    isOpenAction: boolean;
    isOpenAdminAction: boolean;
    isActive: boolean = false;
    currentPage: string;
    showEditor: boolean=false;
    editMode: string;
    selectedEntry:any;
    searchText:string = '';
    loading: boolean = false;

    pageLength = 0;
    pageIndex = 0;
    pageSize = 10;

    constructor(public elementRef: ElementRef,
        private companyService: CompanyService,
        // public userService: UserService  
        public loginService: LoginService
        ) { 
    }

    ngOnDestroy(): void {
        
    }

    ngAfterViewInit() {
      this.loadCompanyData();
    }

    ngAfterContentInit() {
    }

    ngOnInit() {
      this.userRole=this.loginService.role;
      console.log("userRole",this.userRole)
      if(this.userRole == "admin") {
        this.displayedColumns.push('action');
      }
      // this.dataSource = new MatTableDataSource(this.mockTableData);
      this.dataSource = new MatTableDataSource(this.tableData);
      this.dataSource.sort = this.sort;
      this.dataSource.paginator = this.paginator;
    }

    pageChanged(event) {
      // console.log("**** pageChanged, event: ", event);
      this.pageIndex = event.pageIndex;
      this.loadCompanyData(false);
    }

    search() {
      this.loadCompanyData(false);
    }

    private loadCompanyData(fromFirstPage : boolean = false) {
      if(fromFirstPage) {
        this.pageIndex = 0;
      }
      this.loading = true;
      //this.companyService.listCompanies().subscribe((response: any) => {
      this.companyService.listCompaniesByPage(this.pageIndex, this.pageSize, this.searchText).subscribe((response: any) => {
        // if(response.code == 200 && response.data && response.data.length > 0) {
        //   this.tableData = _.map(response.data, (item) => {
        if(response.code == 200 && response.data && response.data.content && response.data.content.length > 0) {
          this.tableData = _.map(response.data.content, (item) => {
            return {
              id: item.id, 
              companyname: item.companyName, 
              ceoname : item.ceo, 
              turnover: item.turnover, 
              briefWriteUp: item.briefWriteUp, 
              boardDirectors: item.boardDirectors,
              sector: item.sector.sectorName,
              sectorId: item.sector.id,
              stockchangename: item.stockExchange.stockExchange,  
              code: item.companyCode, 
              action: ""}
          });
          // console.log("***** this.tableData: ", this.tableData);
          this.dataSource.data = this.tableData;
          this.pageLength = response.data.totalElements;
          this.companyTable.renderRows();
        }
        this.loading = false;
      });
    }

    // testLoad() {
    //   this.loadCompanyData();
    // }

    showlib(event){
      this.showEditor=false;
      console.log("****** showlib, event: ", event);
      if(event.dataChanged) {
        this.loadCompanyData(true);
      }
    }

    create(){
      this.showEditor=true;
      this.editMode = 'create';
      this.selectedEntry={
        companyname: "Name1", 
        ceoname : '', 
        turnover: 0, 
        briefWriteUp: '',
        boardDirectors:"",
        sector:"",
        stockchangename:"",  
        code:"", 
        action:""
      };
    }

    open(element){
      this.showEditor=true;
      this.editMode = 'update';
      // this.selectedEntry=element;
      this.selectedEntry= {
        id: element.id,
        companyname: element.companyname,
        turnover: element.turnover,
        ceoname: element.ceoname,
        briefWriteUp: element.briefWriteUp,
        boardDirectors: element.boardDirectors,
        sector: element.sectorId
      };
      // console.log("**** edit company, entry: ", this.selectedEntry);
    }
   
    remove(element){
      this.companyService.deleteCompanyByid(element.id).subscribe(resp=> {
        this.loadCompanyData(true);
      });
    }

    // remove(element){
    //   let index = -1;
    //   for (var i = 0; i < this.mockTableData.length; i++) {
    //     if (this.mockTableData[i].id == element.id) {
    //       index = i;
    //       break;
    //     }
    //   };
    //   if(index > -1) {
    //     this.mockTableData.splice(index, 1);
    //     this.companyTable.renderRows();
    //   }
    // }
}
