package com.candyshop.main.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.candyshop.main.model.Productos;

import com.candyshop.main.repository.ProductosRepository;


/**
 * Controlador MVC para gestionar las operaciones CRUD de Ciudades a través de
 * vistas HTML.
 * 
 * @Controller - Indica que esta clase es un controlador Spring MVC
 *             @RequestMapping("/ciudades") - Todas las rutas en este
 *             controlador empiezan con /ciudades
 * 
 *             A diferencia del ApiCiudadController, este controlador:
 *             - Devuelve vistas HTML en lugar de JSON
 *             - Utiliza Model para pasar datos a las vistas
 *             - Maneja formularios y redirecciones
 */
@Controller
@RequestMapping("/productos")
public class productosController {

    /**
     * Logger para registrar eventos y errores
     * Útil para depuración y monitoreo de la aplicación
     */
    private Logger logger = LoggerFactory.getLogger(proveedorController.class);

    /**
     * Repositorio de ciudades inyectado automáticamente
     */
    @Autowired
    private ProductosRepository productosRepository;

    /**
     * Maneja las peticiones GET a /proveedor
     * Muestra la lista de todas los proveedores 
     * 
     * @param model 
     * @return String Nombre de la vista a renderizar (proveedor.html)
     * 
     */
    @GetMapping
    public String listProducts(Model model) {
        // Recuperamos todas las ciudades de la base de datos
        List<Productos> listaProductos = productosRepository.findAll();

        // Añadimos la lista al modelo para que esté disponible en la vista
        // En la vista se accederá como ${productos}
        model.addAttribute("productos", listaProductos);

        // Devolvemos el nombre de la plantilla Thymeleaf
        return "listProduct";
    }

    /**
     * Maneja las peticiones GET a /ciudades/eliminar/{id}
     * Elimina una ciudad por su ID
     * 
     * @param id        ID de la ciudad a eliminar
     * @param redAttrib Objeto para añadir mensajes flash que sobreviven a una
     *                  redirección
     * @return String Redirección a la lista de ciudades
     * 
     *         Conceptos clave:
     *         - @PathVariable: Captura variables de la URL
     *         - RedirectAttributes: Permite pasar mensajes entre redirecciones
     */
    @GetMapping("/eliminar/{productos_id}")
    public String removeProduct(@PathVariable Long id, RedirectAttributes redAttrib) {
        // Verificamos si la ciudad existe antes de intentar eliminarla
        if (!productosRepository.existsById(id)) {
            // Si no existe, añadimos un mensaje de error que se mostrará en la siguiente
            // vista
            redAttrib.addFlashAttribute("error", "La ciudad no Existe");
            // Registramos el error en el log
            logger.error("No existe la ciudad");
        } else {
            productosRepository.deleteById(id);
            redAttrib.addFlashAttribute("success", "Se ha borrado Correctamente el producto  con id " + id);
            logger.info("Se ha borrado Correctamente el producto  con id " + id);
        }

        return "redirect:/ciudades";
    }

    @GetMapping("/nuevo")
    public String newProveedor(Model model) {

        // Creamos una ciudad para que el formulario
        // la asocie a sus datos
        Productos productos = new Productos();
        // La guardamos en el model para que le llegue al formulario
        model.addAttribute("productos", productos);
        // Cargamos la vista nuevaCiudad
        return "nuevoProductos";
    }

    @PostMapping("/crear")
    public String createCity(@ModelAttribute("productos") Productos productos) {

        productosRepository.save(productos);

        logger.info("Se ha creado el producto  con id " + productos.getProducto_id());

        return "redirect:/productos";
    }

    @GetMapping("/editar/{productos_id}")
    public String editProductos(@PathVariable Long id, Model model) {

        // Primero creo un proveedor en blanco
        Productos productos = new Productos();

        // Si no existe el proveedor con id en la bd devuelo el error
        if (!productosRepository.existsById(id))
            model.addAttribute("error", "La ciudad no Existe");
        // Si existe el producto en bd la cargamos
        else
            productos = productosRepository.findById(id).get();
        // Cargamos la ciudad en el model y cargamos la vista
        model.addAttribute("productos", productos);

        return "editarProductos";
    }

    @PostMapping("/modificar")
    public String modifyCity(@ModelAttribute("ciudad") Productos productos, Model model) {

        try {
            // Guardamos el productos
            productosRepository.save(productos);

        } catch (Exception e) {
            model.addAttribute("error", "el PRODUCTOS  no Existe");
            return "redirect:/productos";
        }

        return "redirect:/productos";

    }

}

