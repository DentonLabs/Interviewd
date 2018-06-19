using System.Collections.Generic;
using Interviewd.Common;

namespace Interviewd.Application.Dto
{
    public class InterviewTemplateDto : IIdentifiable
    {
        public string Id { get; set; }

        public string Name { get; set; }

        public IEnumerable<string> QuestionIds { get; set; }
    }
}