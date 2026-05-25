package br.com.gilbertodev.apipetshop.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "tb_role")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role extends EntidadeBase implements GrantedAuthority {

    @Column(unique = true, nullable = false)
    private String nome;

    @Override
    public String getAuthority() {

        return this.nome;
    }
}
