using Microsoft.EntityFrameworkCore;

namespace DemoMSSQL.Models
{
    public partial class CidadeContext : DbContext
    {
        public CidadeContext(DbContextOptions<CidadeContext> options) : base(options)
        {

        }

        public DbSet<Cidade> Cidade { get; set; }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            modelBuilder.Entity<Cidade>(entity =>
            {
                entity.HasKey(e => e.Id)
                    .HasName("PK_Cidade");

                entity.Property(e => e.Id_Estado)
                    .HasColumnName("Id_Estado")
                    .IsRequired();

                entity.Property(e => e.Descricao)
                    .HasColumnName("Descricao")
                    .HasMaxLength(250)
                    .IsRequired();
            });

            OnModelCreatingPartial(modelBuilder);
        }

        partial void OnModelCreatingPartial(ModelBuilder modelBuilder);

    }
}
