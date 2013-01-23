
import java.util.Vector;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

/**
 * Clase que extiende la funcionalidad de SoapObject para poder establecer
 * propiedades de tipo array dado que la implementación de KSoap2 tiene la
 * limitación de tratar los objetos de un array como múltiples propiedades con
 * el mismo nombre. Pero si intentamos recuperar la propiedad en cuestión a
 * partir del nombre sólo nos devuelve la primera instancia.
 * 
 * @author BIFMP
 */
public class ExtendedSoapObject extends SoapObject
{

    /**
     * Crea una instancia de {@link ExtendedSoapObject}
     * 
     * @param namespace
     *            namespace del objeto
     * @param name
     *            nombre del objeto
     */
    public ExtendedSoapObject(String namespace, String name)
    {
        super(namespace, name);
    }

    /**
     * Crea una instancia de {@link ExtendedSoapObject} a partir de una
     * instancia de la clase base.
     * 
     * @param o
     *            instancia de {@link SoapObject}
     */
    public ExtendedSoapObject(SoapObject o)
    {
        super(o.getNamespace(), o.getName());

        for (int i = 0; i < o.getPropertyCount(); i++)
        {
            PropertyInfo pi = new PropertyInfo();
            o.getPropertyInfo(i, null, pi);
            addProperty(pi, o.getProperty(i));
        }
    }

    /**
     * Permite pasar objetos de tipo array.
     */
    public SoapObject addProperty(String name, Object value)
    {
        if (value instanceof Object[])
        {
            Object[] subValues = (Object[]) value;
            for (int i = 0; i < subValues.length; i++)
            {
                super.addProperty(name, subValues[i]);
            }
        }
        else
        {
            super.addProperty(name, value);
        }

        return this;
    }

    /**
     * Este método devuelve un objeto {@link SoapObject} o valor primitivo
     * {@link SoapPrimitive} o un array de los mismos. Puede devolver null si es
     * el valor que tiene la propiedad (porque fue lo que devolvió el método
     * remoto) o si no se encuentra la propiedad.
     */
    public Object getProperty(String name)
    {
        Vector result = new Vector();

        PropertyInfo info = new PropertyInfo();

        for (int i = 0; i < getPropertyCount(); i++)
        {
            getPropertyInfo(i, null, info);
            if (info.name != null && info.name.equals(name))
            {
                result.addElement(getProperty(i));
            }
        }

        if (result.size() == 1)
        {
            return result.elementAt(0);
        }
        else if (result.size() > 1)
        {
            Object resultArray[] = new Object[result.size()];
            result.copyInto(resultArray);
            return resultArray;
        }
        else
        {
            return null;
        }
    }

    /**
     * Este método siempre devuelve un array de objetos.
     * 
     * @param name
     * @return
     */
    public Object[] getArrayProperty(String name)
    {
        Object o = getProperty(name);
        Object values[] = null;
        if (o != null)
        {
            if (o instanceof Object[])
            {
                values = (Object[]) o;
            }
            else
            {
                values = new Object[1];
                values[0] = o;
            }
        }

        return values;
    }
}