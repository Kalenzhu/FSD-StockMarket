export class CompanyUpdate {

    companyName: string;
    turnover?: number = 0;
    ceo?: string = '';
    boardDirectors?: string = '';
    briefWriteUp?: string = '';
    picUrl?: string = '';
    sectorId: number;

}
export class CompanyNew extends CompanyUpdate {
    companyStockExchangeList?:{
        stockExchangeId: number,
        companyCode: string
    }[] = [];
}