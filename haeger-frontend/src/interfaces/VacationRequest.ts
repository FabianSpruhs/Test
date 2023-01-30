import {Employee} from "@/interfaces/Employee";

export interface VacationRequest {
    id:number;
    beginDate:string;
    endDate:string;
    status:string;
    employee: Employee;
    vacationDays:number;
}