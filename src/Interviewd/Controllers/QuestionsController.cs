using System.Collections.Generic;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;

namespace Interviewd.Controllers
{
    [Route("questions")]
    public class QuestionsController : Controller
    {
        [HttpGet]
        public async Task<IEnumerable<string>> GetQuestions()
        {
            return new List<string>()
            {
                "test1",
                "test2"
            };
        }
    }
}