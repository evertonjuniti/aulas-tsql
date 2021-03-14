using DemoMSSQL.Filters;
using DemoMSSQL.Models;
using DemoMSSQL.Wrappers;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace DemoMSSQL.Controllers
{
    [Route("api/v1/[controller]")]
    [ApiController]
    public class CidadesController : ControllerBase
    {
        private readonly CidadeContext _context;

        public CidadesController(CidadeContext context)
        {
            _context = context;
        }

        // GET: api/Cidades
        [HttpGet]
        public async Task<ActionResult<IEnumerable<Cidade>>> GetCidades(
            [FromQuery(Name = "descricao")] string? descricao)
        {
            if (descricao != null)
                return await _context.Cidade.Where(filter => filter.Descricao == descricao).ToListAsync();
            else
                return await _context.Cidade.ToListAsync();
        }

        // GET: api/Cidades/5
        [HttpGet("{id}")]
        public async Task<ActionResult<Cidade>> GetCidade(short id)
        {
            var cidade = await _context.Cidade.FindAsync(id);

            if (cidade == null)
            {
                return NotFound();
            }

            return cidade;
        }

        // PUT: api/Cidades/5
        // To protect from overposting attacks, enable the specific properties you want to bind to, for
        // more details, see https://go.microsoft.com/fwlink/?linkid=2123754.
        [HttpPut("{id}")]
        public async Task<ActionResult<Cidade>> PutCidade(short id, Cidade cidade)
        {
            if (id != cidade.Id)
                return BadRequest("Id da Cidade não corresponde com o payload da requisição.");

            if (cidade.Id_Estado <= 0)
                return BadRequest("Id do Estado não pode ser menor ou igual a zero.");

            if (cidade.Descricao == null)
                return BadRequest("Id do Estado não pode ser nulo.");

            _context.Entry(cidade).State = EntityState.Modified;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!CidadeExists(id))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return cidade;
        }

        [HttpPatch("{id}")]
        public async Task<ActionResult<Cidade>> PatchCidade(short id, Cidade cidade)
        {
            if (cidade.Descricao == null)
                return BadRequest("Id do Estado não pode ser nulo.");

            var cidadePatch = await _context.Cidade.FindAsync(id);
            cidadePatch.Descricao = cidade.Descricao;
            _context.Entry(cidadePatch).State = EntityState.Modified;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!CidadeExists(id))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return cidadePatch;
        }

        // POST: api/Cidades
        // To protect from overposting attacks, enable the specific properties you want to bind to, for
        // more details, see https://go.microsoft.com/fwlink/?linkid=2123754.
        [HttpPost]
        public async Task<ActionResult<Cidade>> PostCidade(Cidade cidade)
        {
            _context.Cidade.Add(cidade);
            await _context.SaveChangesAsync();

            return CreatedAtAction("GetCidade", new { id = cidade.Id }, cidade);
        }

        // DELETE: api/Cidades/5
        [HttpDelete("{id}")]
        public async Task<ActionResult<IDictionary<string, bool>>> DeleteCidade(short id)
        {
            var cidade = await _context.Cidade.FindAsync(id);
            if (cidade == null)
            {
                return NotFound();
            }

            _context.Cidade.Remove(cidade);
            await _context.SaveChangesAsync();

            IDictionary<string, bool> resultado = new Dictionary<string, bool>();
            resultado.Add("deletado", true);
            
            return Ok(resultado);
        }

        [HttpGet("paginado")]
        public async Task<ActionResult<IEnumerable<Cidade>>> GetCidadesPaginado(
            [FromQuery] PaginationFilter filter)
        {
            var validFilter = new PaginationFilter(filter.PageNumber, filter.PageSize);
            var pagedData = await _context.Cidade
                .Skip((validFilter.PageNumber - 1) * validFilter.PageSize)
                .Take(validFilter.PageSize)
                .ToListAsync();
            var totalRecords = await _context.Cidade.CountAsync();
            return Ok(new PagedResponse<List<Cidade>>(pagedData, validFilter.PageNumber, validFilter.PageSize, totalRecords));
        }

        private bool CidadeExists(short id)
        {
            return _context.Cidade.Any(e => e.Id == id);
        }
    }
}
