package org.example.tiendafinal.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductoResponse {
    private List<Producto> products;
    private long total;
    private long skip;
    private long limit;

    public void mostrarDatos(){
        System.out.println(products);
        System.out.println(total);
        System.out.println(skip);
        System.out.println(limit);
    }
}
