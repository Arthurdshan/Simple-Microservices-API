import { BaseEntity, Column, Entity, PrimaryColumn } from "typeorm";

@Entity('User')
export class User extends BaseEntity {

    @PrimaryColumn({
        type: "int"
    })
    id!: number;

    @Column({
        type: "varchar",
        length: 255
    })
    name!: string;

    @Column({
        type: "varchar",
        length: 255,
        unique: true
    })
    email!: string;

    @Column({
        type: "varchar",
        length: 255
    })
    password!: string;
}