import { Component, OnInit, OnDestroy, ViewChild, ElementRef, AfterViewInit, AfterContentInit, ViewChildren, QueryList } from '@angular/core';
// import { UserService } from 'src/app/service/user.service';
import {MatSort, MatTableDataSource, MatTable} from '@angular/material';
import {MatPaginator} from '@angular/material';
import { LoginService } from 'src/app/service/login-service';
import { IPOService } from 'src/app/service/ipo.service';
import * as _ from "lodash";
import * as moment from 'moment';

export interface TableElement {
  id: number;
  companyname: string;
  stockchange: string;
  price: number;
  numberofshare: number;
  opendatetime: string;
  remarks: string;
  action:string;
}

@Component({
  selector: 'ipo-table',
  templateUrl: './ipo-table.component.html',
  styleUrls: ['./ipo-table.component.scss'],
})
export class IPOTableComponent implements OnInit,OnDestroy,AfterViewInit,AfterContentInit {

    // mockTableData: TableElement[]  = [
    //   {id: 1, companyname: "BMW", stockchange : 'NSE', price: 33.22, numberofshare: 100000,opendatetime:"2019-12-12",remarks:"BMW", action:""},
    //   {id: 2, companyname: "Mercedez Benz", stockchange : 'BSE', price: 11.9, numberofshare: 2000000,opendatetime:"2019-11-11",remarks:"BENZ", action:""},
    // ];

    pushRightClass: string = 'push-right';
    userRole:string;
    showAdminMenu: boolean;
    isOpenAction: boolean;
    isOpenAdminAction: boolean;
    isActive: boolean = false;
    currentPage: string;
    showEditor: boolean=false;
    selectedEntry:any;

    tableData: TableElement[] = [];
    pageLength = 0;
    pageIndex = 0;
    pageSize = 10;
    editMode: string;

    @ViewChild(MatTable) ipoTable: MatTable<any>;
    @ViewChild(MatSort) sort: MatSort;
    @ViewChild(MatPaginator) paginator: MatPaginator;
    displayedColumns: string[] = ['companyname', 'stockchange', 'price', 'numberofshare', 'opendatetime', 'remarks'];
    dataSource: any;

    constructor(public elementRef: ElementRef,
        // public userService: UserService
        private ipoService: IPOService,
        public loginService: LoginService
        ) { 
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
      // this.loadIPOData();
    }

    pageChanged(event) {
      // console.log("**** pageChanged, event: ", event);
      this.pageIndex = event.pageIndex;
      this.loadIPOData(false);
    }

    private loadIPOData(fromFirstPage : boolean = false) {
      if(fromFirstPage) {
        this.pageIndex = 0;
      }
      this.ipoService.getIPOByPage(this.pageIndex, this.pageSize).subscribe((response: any) => {
        if(response.code == 200 && response.data && response.data.content && response.data.content.length > 0) {
          this.tableData = _.map(response.data.content, (item) => {
            return {
              id: item.id, 
              companyId: item.company.id, 
              companyname: item.company.companyName, 
              stockchangeId: item.stockExchange.id,
              stockchange: item.stockExchange.stockExchange, 
              price : item.pricePerShare, 
              numberofshare: item.totalShares, 
              opendatetime: item.openDatetime, 
              remarks: item.remarks,
              action: ""
            }
          });
          console.log("***** this.tableData: ", this.tableData);
          this.dataSource.data = this.tableData;
          this.pageLength = response.data.totalElements;
          this.ipoTable.renderRows();
        }
      });
    }

    ngOnDestroy(): void {
        
    }

    ngAfterViewInit() {
      this.loadIPOData();
    }

    ngAfterContentInit() {
    }
  
    showlib(event){
      this.showEditor=false;
      console.log("****** showlib, event: ", event);
      if(event.dataChanged) {
        this.loadIPOData(true);
      }
    }

    create(){
      this.showEditor=true;
      this.editMode = 'create';
      this.selectedEntry= {
        companyname: "Name1", 
        stockchange : '', 
        price: 0, 
        numberofshare: 0,
        opendatetime: '',
        remarks: ''
      };
    }

    open(element){
      this.showEditor=true;
      this.editMode = 'update';
      this.selectedEntry= {
        id: element.id,
        companyname: element.companyId, 
        stockchange : element.stockchangeId, 
        price: element.price, 
        numberofshare: element.numberofshare,
        opendatetime: moment(element.opendatetime).toDate(),
        remarks: element.remarks
      };
    }
   
    remove(element){
      this.ipoService.deleteIPOByid(element.id).subscribe(resp=> {
        this.loadIPOData(true);
      });
    }

    formatDate(element) {
      return moment(element.opendatetime).format('YYYY-MM-DD');
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
    //     this.ipoTable.renderRows();
    //   }
    // }
}
