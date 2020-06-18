export class IPONewOrUpdate {
    companyId: number;
    stockexchangeId: number;
    pricePerShare: number;
    totalShares: number;
    openDatetimeStr: string;
    remarks?: string = '';
}