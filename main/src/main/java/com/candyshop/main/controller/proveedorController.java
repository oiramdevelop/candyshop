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
import com.candyshop.main.model.Proveedor;
import com.candyshop.main.repository.ProductosRepository;
import com.candyshop.main.repository.ProveedorRepository;
import org.springframework.web.bind.annotation.RequestParam;


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
@RequestMapping("/proveedores")
public class proveedorController {

    /**
     * Logger para registrar eventos y errores
     * Útil para depuración y monitoreo de la aplicación
     */
    private Logger logger = LoggerFactory.getLogger(proveedorController.class);

    /**
     * Repositorio de ciudades inyectado automáticamente
     */
    @Autowired
    private ProveedorRepository proveedorRepository;
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
    public String listProvedore(Model model) {
        // Recuperamos todas las ciudades de la base de datos
        List<Proveedor> listaProveedor = proveedorRepository.findAll();

        // Añadimos la lista al modelo para que esté disponible en la vista
        // En la vista se accederá como ${ciudades}
        model.addAttribute("proveedores", listaProveedor);

        // Devolvemos el nombre de la plantilla Thymeleaf
        return "listProveedores";
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
    @GetMapping("/eliminar/{proveedor_id}")
    public String removeCity(@PathVariable Long id, RedirectAttributes redAttrib) {
        // Verificamos si la ciudad existe antes de intentar eliminarla
        if (!proveedorRepository.existsById(id)) {
            // Si no existe, añadimos un mensaje de error que se mostrará en la siguiente
            // vista
            redAttrib.addFlashAttribute("error", "La ciudad no Existe");
            // Registramos el error en el log
            logger.error("No existe la ciudad");
        } else {
            proveedorRepository.deleteById(id);
            redAttrib.addFlashAttribute("success", "Se ha borrado Correctamente la ciudad con id " + id);
            logger.info("Se ha borrado Correctamente la ciudad con id " + id);
        }

        return "redirect:/ciudades";
    }

    @GetMapping("/nuevo")
    public String newProveedor(Model model) {

        // Creamos una ciudad para que el formulario
        // la asocie a sus datos
        Proveedor ciudad = new Proveedor();
        // La guardamos en el model para que le llegue al formulario
        model.addAttribute("ciudad", ciudad);
        // Cargamos la vista nuevaCiudad
        return "nuevoProveedor";
    }

    @PostMapping("/crear")
    public String createCity(@ModelAttribute("proveedor") Proveedor proveedor) {

        proveedorRepository.save(proveedor);

        logger.info("Se ha creado la ciudad con id " + proveedor.getProveedor_id());

        return "redirect:/proveedor";
    }

    @GetMapping("/editar/{proveedor_id}")
    public String editCity(@PathVariable Long id, Model model) {

        // Primero creo un proveedor en blanco
        Proveedor proveedor = new Proveedor();

        // Si no existe el proveedor con id en la bd devuelo el error
        if (!proveedorRepository.existsById(id))
            model.addAttribute("error", "La ciudad no Existe");
        // Si existe la proveedor en bd la cargamos
        else
            proveedor = proveedorRepository.findById(id).get();
        // Cargamos la ciudad en el model y cargamos la vista
        model.addAttribute("proveedor", proveedor);

        return "editarProveedor";
    }

    @PostMapping("/modificar")
    public String modifyCity(@ModelAttribute("ciudad") Proveedor proveedor, Model model) {

        try {
            // Guardamos el proveedor
            proveedorRepository.save(proveedor);

        } catch (Exception e) {
            model.addAttribute("error", "el Proveedor  no Existe");
            return "redirect:/proveedor";
        }

        return "redirect:/proveedor";

    }



@GetMapping("/{proveedorId}/productos")
public String listarProductosPorProveedor(@PathVariable("proveedorId") Long proveedorId, Model model) {

    // ✅ Usar el método corregido
    List<Productos> productosProveedor = productosRepository.findByProveedorId(proveedorId);

    Proveedor proveedor = proveedorRepository.findById(proveedorId).orElse(null);

    model.addAttribute("proveedor", proveedor);
    model.addAttribute("productos", productosProveedor);

    return "listProductByProveedor";
}


}


