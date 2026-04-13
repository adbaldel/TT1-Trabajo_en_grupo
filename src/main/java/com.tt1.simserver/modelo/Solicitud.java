package com.tt1.simserver.modelo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@JsonTypeName("Solicitud")
@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaJAXRSSpecServerCodegen", date = "2026-04-13T19:26:41.786837200+02:00[Europe/Madrid]", comments = "Generator version: 7.4.0")
public class Solicitud
{
    private @Valid List<Integer> cantidadesIniciales;
    private @Valid List<String> nombreEntidades;

    /**
     *
     **/
    public Solicitud cantidadesIniciales(List<Integer> cantidadesIniciales)
    {
        this.cantidadesIniciales = cantidadesIniciales;
        return this;
    }


    @ApiModelProperty(value = "")
    @JsonProperty("cantidadesIniciales")
    public List<Integer> getCantidadesIniciales()
    {
        return cantidadesIniciales;
    }

    @JsonProperty("cantidadesIniciales")
    public void setCantidadesIniciales(List<Integer> cantidadesIniciales)
    {
        this.cantidadesIniciales = cantidadesIniciales;
    }

    public Solicitud addCantidadesInicialesItem(Integer cantidadesInicialesItem)
    {
        if (this.cantidadesIniciales == null)
        {
            this.cantidadesIniciales = new ArrayList<>();
        }

        this.cantidadesIniciales.add(cantidadesInicialesItem);
        return this;
    }

    public Solicitud removeCantidadesInicialesItem(Integer cantidadesInicialesItem)
    {
        if (cantidadesInicialesItem != null && this.cantidadesIniciales != null)
        {
            this.cantidadesIniciales.remove(cantidadesInicialesItem);
        }

        return this;
    }

    /**
     *
     **/
    public Solicitud nombreEntidades(List<String> nombreEntidades)
    {
        this.nombreEntidades = nombreEntidades;
        return this;
    }


    @ApiModelProperty(value = "")
    @JsonProperty("nombreEntidades")
    public List<String> getNombreEntidades()
    {
        return nombreEntidades;
    }

    @JsonProperty("nombreEntidades")
    public void setNombreEntidades(List<String> nombreEntidades)
    {
        this.nombreEntidades = nombreEntidades;
    }

    public Solicitud addNombreEntidadesItem(String nombreEntidadesItem)
    {
        if (this.nombreEntidades == null)
        {
            this.nombreEntidades = new ArrayList<>();
        }

        this.nombreEntidades.add(nombreEntidadesItem);
        return this;
    }

    public Solicitud removeNombreEntidadesItem(String nombreEntidadesItem)
    {
        if (nombreEntidadesItem != null && this.nombreEntidades != null)
        {
            this.nombreEntidades.remove(nombreEntidadesItem);
        }

        return this;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }
        Solicitud solicitud = (Solicitud) o;
        return Objects.equals(this.cantidadesIniciales, solicitud.cantidadesIniciales) &&
                Objects.equals(this.nombreEntidades, solicitud.nombreEntidades);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(cantidadesIniciales, nombreEntidades);
    }

    @Override
    public String toString()
    {

        String sb = "class Solicitud {\n" +
                "    cantidadesIniciales: " + toIndentedString(cantidadesIniciales) + "\n" +
                "    nombreEntidades: " + toIndentedString(nombreEntidades) + "\n" +
                "}";
        return sb;
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(Object o)
    {
        if (o == null)
        {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }


}

