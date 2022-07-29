package com.rest.springbootemployee.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String companyName;



    @OneToMany(fetch=FetchType.EAGER)
    @JoinColumn(name = "companyId")
    @ToString.Exclude
    private List<Employee> employees;

    public Company(int id, String companyName, Employee employee) {
        this.id = id;
        this.companyName = companyName;
        if (this.getEmployees()!=null){
            this.getEmployees().add(employee);
            return;
        }
        this.setEmployees(new ArrayList<>());
        this.getEmployees().add(employee);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Company company = (Company) o;
        return id != null && Objects.equals(id, company.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
