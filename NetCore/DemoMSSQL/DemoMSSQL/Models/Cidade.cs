using System.ComponentModel.DataAnnotations.Schema;

namespace DemoMSSQL.Models
{
    public class Cidade
    {
        public short Id { get; set; }
        public byte Id_Estado { get; set; }
        //[Column("Descricao", TypeName = "VARCHAR(250)")]
        public string Descricao { get; set; }
    }
}
