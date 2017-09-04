using System.Threading.Tasks;
using Interviewd.Domain.Model;

namespace Interviewd.Infrastructure.Abstraction
{
    public interface IInterviewRepository
    {
        Task<Interview> InsertInterview();
    }
}